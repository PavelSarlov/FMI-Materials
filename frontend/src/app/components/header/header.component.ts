import { Component, OnInit } from '@angular/core';
import { CrossEventService } from '../../services/cross-event.service';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  sidenavToggled: boolean = true;
  user$!: Observable<User | null>;

  constructor(
    private crossEventService: CrossEventService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.user$ = this.authService.user$;

    this.crossEventService.toggleSidenav.subscribe(
      (status) => (this.sidenavToggled = status)
    );
  }

  toggleSidenav() {
    this.crossEventService.toggleSidenav.emit(!this.sidenavToggled);
  }

  onLogout() {
    this.authService.logout();
  }
}
