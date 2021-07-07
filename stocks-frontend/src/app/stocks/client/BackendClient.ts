import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class BackendClient {
  private stocksUrl = '/api/stocks';
  constructor(private http: HttpClient) {
  }
  getAllStocks() {
    return this.http.get(this.stocksUrl);
  }

}
