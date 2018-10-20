import { Component, Input } from '@angular/core';

import { Visite } from './visite.model';

@Component({
    selector: 'jhi-visite-list',
    templateUrl: './visite-list.component.html'
})
export class VisiteListComponent {
    @Input() visites: Visite[];
    @Input() fromDb: boolean = false;
    trackId(index: number, item: Visite) {
        return item.id;
    }
}
