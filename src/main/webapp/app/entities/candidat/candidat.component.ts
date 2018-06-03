import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Candidat } from './candidat.model';
import { CandidatService } from './candidat.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-candidat',
    templateUrl: './candidat.component.html'
})
export class CandidatComponent implements OnInit, OnDestroy {
candidats: Candidat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private candidatService: CandidatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.candidatService.query().subscribe(
            (res: HttpResponse<Candidat[]>) => {
                this.candidats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCandidats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Candidat) {
        return item.id;
    }
    registerChangeInCandidats() {
        this.eventSubscriber = this.eventManager.subscribe('candidatListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
