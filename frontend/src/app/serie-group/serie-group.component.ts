import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Serie } from '../models/serie.model';

@Component({
  selector: 'app-serie-group',
  templateUrl: './serie-group.component.html',
  styleUrls: ['./serie-group.component.css']
})
export class SerieGroupComponent {
  @Input() title = '';
  @Input() series: Serie[] = [];
  @Input() emptyMessage = 'No hay elementos disponibles.';
  @Output() selectSerie = new EventEmitter<number>();

  handleSeriesSelection(seriesId?: number): void {
    if (seriesId !== undefined) {
      this.selectSerie.emit(seriesId);
    }
  }
}
