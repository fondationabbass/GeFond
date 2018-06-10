import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SessionProjet } from './session-projet.model';
import { SessionProjetPopupService } from './session-projet-popup.service';
import { SessionProjetService } from './session-projet.service';

@Component({
    selector: 'jhi-session-projet-delete-dialog',
    templateUrl: './session-projet-delete-dialog.component.html'
})
export class SessionProjetDeleteDialogComponent {

    sessionProjet: SessionProjet;

    constructor(
        private sessionProjetService: SessionProjetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sessionProjetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sessionProjetListModification',
                content: 'Deleted an sessionProjet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-session-projet-delete-popup',
    template: ''
})
export class SessionProjetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sessionProjetPopupService: SessionProjetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sessionProjetPopupService
                .open(SessionProjetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
