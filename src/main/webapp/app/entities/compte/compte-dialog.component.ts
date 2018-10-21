import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Compte } from './compte.model';
import { ComptePopupService } from './compte-popup.service';
import { CompteService } from './compte.service';
import { Client } from '../client';
import { Pret } from '../pret';
import { Caisse, CaisseService } from '../caisse';
import { Chapitre, ChapitreService } from '../chapitre';

@Component({
    selector: 'jhi-compte-dialog',
    templateUrl: './compte-dialog.component.html'
})
export class CompteDialogComponent implements OnInit {

    compte: Compte;
    codeChapitre: number;
    isSaving: boolean;
    caisses: Caisse[];

    chapitres: Chapitre[];
    dateOuvertureDp: any;
    dateDernierCreditDp: any;
    dateDernierDebitDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private compteService: CompteService,
        private caisseService: CaisseService,
        private chapitreService: ChapitreService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.caisseService.query()
            .subscribe((res: HttpResponse<Caisse[]>) => { this.caisses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.chapitreService.query({'categorieCompte.equals': '00'})
            .subscribe((res: HttpResponse<Chapitre[]>) => { this.chapitres = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
    updateNum(chapitre: Chapitre) {
        this.compteService.query({'chapitreId.equals': chapitre.id})
            .subscribe((res: HttpResponse<Compte[]>) => {
                const inc = res.body.length + 1;
                this.compte.numCompte = chapitre.numero + '-' + inc + '-' + chapitre.categorieCompte;
                this.codeChapitre = chapitre.numero;
            }, (res: HttpErrorResponse) => this.onError(res.message));

    }
    updateCodeNum(codeChapitre: number) {
        this.chapitreService.query({'numero.equals': codeChapitre})
            .subscribe((res: HttpResponse<Chapitre[]>) => {
                const chapitre = res.body[0];
                this.compte.chapitre = chapitre;
                this.updateNum(chapitre);
            }, (res: HttpErrorResponse) => this.onError(res.message));

    }
    save() {
        this.isSaving = true;
        if (this.compte.id !== undefined) {
            this.subscribeToSaveResponse(
                this.compteService.update(this.compte));
        } else {
            this.subscribeToSaveResponse(
                this.compteService.create(this.compte));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Compte>>) {
        result.subscribe((res: HttpResponse<Compte>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Compte) {
        this.eventManager.broadcast({ name: 'compteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClientById(index: number, item: Client) {
        return item.id;
    }

    trackPretById(index: number, item: Pret) {
        return item.id;
    }

    trackCaisseById(index: number, item: Caisse) {
        return item.id;
    }

    trackChapitreById(index: number, item: Chapitre) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-compte-popup',
    template: ''
})
export class ComptePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private comptePopupService: ComptePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.comptePopupService
                    .open(CompteDialogComponent as Component, params['id']);
            } else {
                this.comptePopupService
                    .open(CompteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
