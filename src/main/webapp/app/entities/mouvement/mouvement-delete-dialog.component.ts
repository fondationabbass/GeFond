import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Mouvement } from './mouvement.model';
import { MouvementPopupService } from './mouvement-popup.service';
import { MouvementService } from './mouvement.service';

@Component({
    selector: 'jhi-mouvement-delete-dialog',
    templateUrl: './mouvement-delete-dialog.component.html'
})
export class MouvementDeleteDialogComponent {

    mouvement: Mouvement;

    constructor(
        private mouvementService: MouvementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mouvementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mouvementListModification',
                content: 'Deleted an mouvement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mouvement-delete-popup',
    template: ''
})
export class MouvementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mouvementPopupService: MouvementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mouvementPopupService
                .open(MouvementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
