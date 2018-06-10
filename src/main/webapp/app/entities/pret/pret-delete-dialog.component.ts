import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pret } from './pret.model';
import { PretPopupService } from './pret-popup.service';
import { PretService } from './pret.service';

@Component({
    selector: 'jhi-pret-delete-dialog',
    templateUrl: './pret-delete-dialog.component.html'
})
export class PretDeleteDialogComponent {

    pret: Pret;

    constructor(
        private pretService: PretService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pretService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pretListModification',
                content: 'Deleted an pret'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pret-delete-popup',
    template: ''
})
export class PretDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pretPopupService: PretPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pretPopupService
                .open(PretDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
