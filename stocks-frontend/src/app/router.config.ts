import {Routes} from '@angular/router';
import {StocksComponent} from './stocks/stocks.component';
import {StocksListComponent} from './stocks/list/stocks-list.component';

export const routerConfig: Routes = [
  {
    path: '',
    component: StocksComponent
  }
  , {
    path: 'ListStocks',
    component: StocksListComponent
  }
  ];
