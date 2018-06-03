import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Compte } from './compte.model';
import { ComptePopupService } from './compte-popup.service';
import { CompteService } from './compte.service';

@Component({
    selector: 'jhi-compte-delete-dialog',
    templateUrl: './compte-delete-dialog.component.html'
})
export class CompteDeleteDialogComponent {

    compte: Compte;

    constructor(
        private compteService: CompteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'compteListModification',
                content: 'Deleted an compte'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compte-delete-popup',
    template: ''
})
export class CompteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private comptePopupService: ComptePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.comptePopupService
                .open(CompteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
