import { Component, Input } from '@angular/core';

import { Entretien } from './entretien.model';

@Component({
    selector: 'jhi-entretien-list',
    templateUrl: './entretien-list.component.html'
})
export class EntretienListComponent {
    @Input() entretiens: Entretien[];
    @Input() fromDb: boolean = false;
    trackId(index: number, item: Entretien) {
        return item.id;
    }
}
