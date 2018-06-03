import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Entretien } from './entretien.model';
import { EntretienPopupService } from './entretien-popup.service';
import { EntretienService } from './entretien.service';

@Component({
    selector: 'jhi-entretien-delete-dialog',
    templateUrl: './entretien-delete-dialog.component.html'
})
export class EntretienDeleteDialogComponent {

    entretien: Entretien;

    constructor(
        private entretienService: EntretienService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entretienService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entretienListModification',
                content: 'Deleted an entretien'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entretien-delete-popup',
    template: ''
})
export class EntretienDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entretienPopupService: EntretienPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entretienPopupService
                .open(EntretienDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
