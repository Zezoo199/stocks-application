import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { StocksComponent } from './stocks/stocks.component';
import { StocksListComponent } from './stocks/list/stocks-list.component';
import {BackendClient} from './stocks/client/BackendClient';
import {routerConfig} from './router.config';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    StocksComponent,
    StocksListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(routerConfig)
  ],
  providers: [BackendClient],
  bootstrap: [AppComponent]
})
export class AppModule { }
