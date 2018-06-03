import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SessionProjet } from './session-projet.model';
import { SessionProjetPopupService } from './session-projet-popup.service';
import { SessionProjetService } from './session-projet.service';

@Component({
    selector: 'jhi-session-projet-dialog',
    templateUrl: './session-projet-dialog.component.html'
})
export class SessionProjetDialogComponent implements OnInit {

    sessionProjet: SessionProjet;
    isSaving: boolean;
    dateOuvertDp: any;
    dateFermetureDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private sessionProjetService: SessionProjetService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sessionProjet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sessionProjetService.update(this.sessionProjet));
        } else {
            this.subscribeToSaveResponse(
                this.sessionProjetService.create(this.sessionProjet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SessionProjet>>) {
        result.subscribe((res: HttpResponse<SessionProjet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SessionProjet) {
        this.eventManager.broadcast({ name: 'sessionProjetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-session-projet-popup',
    template: ''
})
export class SessionProjetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sessionProjetPopupService: SessionProjetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sessionProjetPopupService
                    .open(SessionProjetDialogComponent as Component, params['id']);
            } else {
                this.sessionProjetPopupService
                    .open(SessionProjetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
