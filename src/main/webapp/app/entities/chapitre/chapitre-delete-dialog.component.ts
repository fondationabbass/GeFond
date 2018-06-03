import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Chapitre } from './chapitre.model';
import { ChapitrePopupService } from './chapitre-popup.service';
import { ChapitreService } from './chapitre.service';

@Component({
    selector: 'jhi-chapitre-delete-dialog',
    templateUrl: './chapitre-delete-dialog.component.html'
})
export class ChapitreDeleteDialogComponent {

    chapitre: Chapitre;

    constructor(
        private chapitreService: ChapitreService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chapitreService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chapitreListModification',
                content: 'Deleted an chapitre'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chapitre-delete-popup',
    template: ''
})
export class ChapitreDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chapitrePopupService: ChapitrePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chapitrePopupService
                .open(ChapitreDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
