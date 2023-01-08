import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { BehaviorSubject, take } from 'rxjs';
import { v4 } from 'uuid';

export interface Topic {
  id: string;
  topic: string;
  callback: any;
}

export interface TopicSub extends Topic {
  sub: any;
}

@Injectable({
  providedIn: 'root',
})
export class StompService {
  private connecting: boolean = false;
  private topicQueue: Topic[] = [];
  private topicSubjects = new Map<string, BehaviorSubject<TopicSub | null>>();

  socket = new SockJS('http://localhost:8080/websocket');
  stompClient = Stomp.over(this.socket);

  subscribe(topic: string, callback: any) {
    const current = {
      id: v4(),
      topic,
      callback,
    };

    this.topicSubjects.set(
      current.id,
      new BehaviorSubject<TopicSub | null>(null)
    );

    const obs$ = this.topicSubjects.get(current.id)?.asObservable();

    // If stomp client is currently connecting add the topic to the queue
    if (this.connecting) {
      this.topicQueue.push(current);
      return obs$;
    }

    if (this.stompClient.connected) {
      // Once we are connected set connecting flag to false
      this.connecting = false;
      this.subscribeToTopic(current);
      return obs$;
    }

    // If stomp client is not connected connect and subscribe to topic
    this.connecting = true;
    this.stompClient.connect({}, (): any => {
      this.connecting = false;
      this.subscribeToTopic(current);

      // Once we are connected loop the queue and subscribe to remaining topics from it
      this.topicQueue.forEach((item) => {
        this.subscribeToTopic(item);
      });

      // Once done empty the queue
      this.topicQueue = [];
    });

    return obs$;
  }

  unsubscribe(topic: TopicSub | null) {
    if (topic) {
      if (topic.sub) {
        topic.sub.unsubscribe();
      }

      this.topicSubjects.get(topic.id)?.complete();
      this.topicSubjects.delete(topic.id);
    }
  }

  private subscribeToTopic(topic: Topic): void {
    this.topicSubjects.get(topic.id)?.next({
      ...topic,
      sub: this.stompClient.subscribe(topic.topic, (response?: string): any => {
        topic.callback(response);
      }),
    });
  }
}
