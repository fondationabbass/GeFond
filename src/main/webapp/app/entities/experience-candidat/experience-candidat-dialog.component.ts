import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExperienceCandidat } from './experience-candidat.model';
import { ExperienceCandidatPopupService } from './experience-candidat-popup.service';
import { ExperienceCandidatService } from './experience-candidat.service';
import { Candidat, CandidatService } from '../candidat';

@Component({
    selector: 'jhi-experience-candidat-dialog',
    templateUrl: './experience-candidat-dialog.component.html'
})
export class ExperienceCandidatDialogComponent implements OnInit {

    experienceCandidat: ExperienceCandidat;
    isSaving: boolean;

    candidats: Candidat[];
    dateDebDp: any;
    dateFinDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private experienceCandidatService: ExperienceCandidatService,
        private candidatService: CandidatService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.candidatService.query()
            .subscribe((res: HttpResponse<Candidat[]>) => { this.candidats = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.experienceCandidat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.experienceCandidatService.update(this.experienceCandidat));
        } else {
            this.subscribeToSaveResponse(
                this.experienceCandidatService.create(this.experienceCandidat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ExperienceCandidat>>) {
        result.subscribe((res: HttpResponse<ExperienceCandidat>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ExperienceCandidat) {
        this.eventManager.broadcast({ name: 'experienceCandidatListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCandidatById(index: number, item: Candidat) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-experience-candidat-popup',
    template: ''
})
export class ExperienceCandidatPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private experienceCandidatPopupService: ExperienceCandidatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.experienceCandidatPopupService
                    .open(ExperienceCandidatDialogComponent as Component, params['id']);
            } else {
                this.experienceCandidatPopupService
                    .open(ExperienceCandidatDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
