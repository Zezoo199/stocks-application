export class Stock {
  id: number;
  name: string;
  currentPrice: string;
  lastUpdate: Date;
  constructor(id: number, name: string, currentPrice: string, lastUpdate: Date) {
    this.id = id;
    this.name = name;
    this.currentPrice = currentPrice;
    this.lastUpdate = lastUpdate;
  }
}
