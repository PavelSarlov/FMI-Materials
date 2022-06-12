import { Component, OnInit } from '@angular/core';
import { CrossEventService } from '../../services/cross-event.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent implements OnInit {
  sidenavToggled: boolean = true;

  constructor(private crossEventService: CrossEventService) {}

  ngOnInit(): void {
    this.toggleSidenav();
    this.crossEventService.toggleSidenav.subscribe((status) => {
      this.sidenavToggled = status;
      this.toggleSidenav();
    });
  }

  toggleSidenav() {
    if (this.sidenavToggled) {
      document
        .getElementsByTagName('app-sidenav')[0]
        .setAttribute('style', "width: var(--app-sidenav-width);");
      document
        .getElementsByTagName('main')[0]
        .setAttribute('style', "margin-left: var(--app-sidenav-width);");
    } else {
      document
        .getElementsByTagName('app-sidenav')[0]
        .setAttribute('style', "width: 0;");
      document
        .getElementsByTagName('main')[0]
        .setAttribute('style', "margin-left: 0;");
    }
  }
}
