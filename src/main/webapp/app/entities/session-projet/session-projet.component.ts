import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SessionProjet } from './session-projet.model';
import { SessionProjetService } from './session-projet.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-session-projet',
    templateUrl: './session-projet.component.html'
})
export class SessionProjetComponent implements OnInit, OnDestroy {
sessionProjets: SessionProjet[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sessionProjetService: SessionProjetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sessionProjetService.query().subscribe(
            (res: HttpResponse<SessionProjet[]>) => {
                this.sessionProjets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSessionProjets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SessionProjet) {
        return item.id;
    }
    registerChangeInSessionProjets() {
        this.eventSubscriber = this.eventManager.subscribe('sessionProjetListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
