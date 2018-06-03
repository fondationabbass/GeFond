import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Echeance } from './echeance.model';
import { EcheancePopupService } from './echeance-popup.service';
import { EcheanceService } from './echeance.service';

@Component({
    selector: 'jhi-echeance-delete-dialog',
    templateUrl: './echeance-delete-dialog.component.html'
})
export class EcheanceDeleteDialogComponent {

    echeance: Echeance;

    constructor(
        private echeanceService: EcheanceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.echeanceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'echeanceListModification',
                content: 'Deleted an echeance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-echeance-delete-popup',
    template: ''
})
export class EcheanceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private echeancePopupService: EcheancePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.echeancePopupService
                .open(EcheanceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
