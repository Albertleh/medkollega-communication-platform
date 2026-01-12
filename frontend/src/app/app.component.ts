import { Component, ViewChild, OnDestroy } from '@angular/core';
import { AuthService } from './services/auth.service';
import { Router, NavigationEnd } from '@angular/router';
import { filter, Subscription } from 'rxjs';

import { LeftSidebarComponent } from './components/left-sidebar/left-sidebar.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  standalone: false
})
export class AppComponent implements OnDestroy {

  title = 'MedKollega';

  @ViewChild('sidebar') sidebar!: LeftSidebarComponent;

  isSidebarOpen = true;
  isLoginRoute = false;

  private routerSub?: Subscription;
  private patientUpdatedSub?: Subscription;

  constructor(
    private router: Router,
    public authService: AuthService
  ) {
    this.routerSub = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        const url = event.urlAfterRedirects ?? event.url;
        this.isLoginRoute = url.includes('/login');
      });
  }

  ngOnDestroy(): void {
    this.routerSub?.unsubscribe();
    this.patientUpdatedSub?.unsubscribe();
  }

  toggleSidebar(): void {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  onPatientSelected(id: string) {
    console.log('Selected patient:', id);
  }

  // wird durch (activate) vom router-outlet aufgerufen
  onRouteActivate(component: any): void {
    // alte Subscription entfernen wenn Route wechselt
    this.patientUpdatedSub?.unsubscribe();
    this.patientUpdatedSub = undefined;

    // "duck typing": wenn routed component ein patientUpdated EventEmitter hat, subscribe
    if (component?.patientUpdated?.subscribe) {
      this.patientUpdatedSub = component.patientUpdated.subscribe(() => {
        this.sidebar?.reloadPatients();
      });
    }
  }
}
