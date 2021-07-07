import { Component, OnInit } from '@angular/core';
import {BackendClient} from '../client/BackendClient';
import {throwError} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {Stock} from '../model/Stock';

@Component({
  selector: 'app-list',
  templateUrl: './stocks-list.component.html',
  styleUrls: ['./stocks-list.component.css']
})
export class StocksListComponent implements OnInit {
  stocks: Stock[];
  errorMessage: string;
  spin = true;
  constructor(private backendClient: BackendClient) {
  }
  ngOnInit() {
    this.errorMessage = '';
    this.stocks = [];
    this.backendClient.getAllStocks().pipe(
      catchError((error: HttpErrorResponse) => {
          this.errorMessage = error.message + ' and Body = ' + error.error;
          return throwError(error);
        }
      )
    ).subscribe((stocks: Stock[]) => {
      this.stocks = stocks;
      this.spin = false;
    });
  }

}
