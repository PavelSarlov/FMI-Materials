import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user?: User | null = JSON.parse(localStorage.getItem('user')!);
  
  constructor() { }

  ngOnInit(): void {
  }

}
