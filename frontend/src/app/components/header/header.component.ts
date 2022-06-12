import { Component, OnInit } from '@angular/core';
import { CrossEventService } from '../../services/cross-event.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  sidenavToggled: boolean = true;

  constructor(private crossEventService: CrossEventService) {}

  ngOnInit(): void {
    this.crossEventService.toggleSidenav.subscribe(
      (status) => (this.sidenavToggled = status)
    );
  }

  toggleSidenav() {
    this.crossEventService.toggleSidenav.emit(!this.sidenavToggled);
  }
}
