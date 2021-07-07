import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StocksListComponent } from './stocks-list.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {BackendClient} from '../client/BackendClient';

describe('StocksListComponent', () => {
  let component: StocksListComponent;
  let fixture: ComponentFixture<StocksListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StocksListComponent ],
      imports: [ReactiveFormsModule,
        HttpClientModule],
      providers: [BackendClient]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StocksListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  function getStocks() {
  }
});
