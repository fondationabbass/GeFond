import { Component, Input } from '@angular/core';

import { Document } from './document.model';

@Component({
    selector: 'jhi-document-list',
    templateUrl: './document-list.component.html'
})
export class DocumentListComponent {
    @Input() documents: Document[];
    @Input() fromDb: boolean = false;
    trackId(index: number, item: Document) {
        return item.id;
    }
}
