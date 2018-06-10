import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Parametrage } from './parametrage.model';
import { ParametragePopupService } from './parametrage-popup.service';
import { ParametrageService } from './parametrage.service';

@Component({
    selector: 'jhi-parametrage-delete-dialog',
    templateUrl: './parametrage-delete-dialog.component.html'
})
export class ParametrageDeleteDialogComponent {

    parametrage: Parametrage;

    constructor(
        private parametrageService: ParametrageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametrageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parametrageListModification',
                content: 'Deleted an parametrage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametrage-delete-popup',
    template: ''
})
export class ParametrageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametragePopupService: ParametragePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parametragePopupService
                .open(ParametrageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
