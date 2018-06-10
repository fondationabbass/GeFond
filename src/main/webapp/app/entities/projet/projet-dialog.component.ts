import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Projet } from './projet.model';
import { ProjetPopupService } from './projet-popup.service';
import { ProjetService } from './projet.service';
import { Candidature, CandidatureService } from '../candidature';

@Component({
    selector: 'jhi-projet-dialog',
    templateUrl: './projet-dialog.component.html'
})
export class ProjetDialogComponent implements OnInit {

    projet: Projet;
    isSaving: boolean;

    candidatures: Candidature[];
    dateCreationDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private projetService: ProjetService,
        private candidatureService: CandidatureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candidatureService
            .query({filter: 'projet-is-null'})
            .subscribe((res: HttpResponse<Candidature[]>) => {
                if (!this.projet.candidature || !this.projet.candidature.id) {
                    this.candidatures = res.body;
                } else {
                    this.candidatureService
                        .find(this.projet.candidature.id)
                        .subscribe((subRes: HttpResponse<Candidature>) => {
                            this.candidatures = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.projet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.projetService.update(this.projet));
        } else {
            this.subscribeToSaveResponse(
                this.projetService.create(this.projet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Projet>>) {
        result.subscribe((res: HttpResponse<Projet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Projet) {
        this.eventManager.broadcast({ name: 'projetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCandidatureById(index: number, item: Candidature) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-projet-popup',
    template: ''
})
export class ProjetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private projetPopupService: ProjetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.projetPopupService
                    .open(ProjetDialogComponent as Component, params['id']);
            } else {
                this.projetPopupService
                    .open(ProjetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
