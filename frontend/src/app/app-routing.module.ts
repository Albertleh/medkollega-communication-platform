import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {PatientViewComponent} from './components/patients/patients.component';
import { AccountOverviewComponent } from './components/account-overview/account-overview.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent},
  { path: 'patients/:id', component: PatientViewComponent },
  { path: 'patients', component: PatientViewComponent },
  { path: 'account', component: AccountOverviewComponent }  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
