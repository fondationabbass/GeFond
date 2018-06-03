import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pret } from './pret.model';
import { PretService } from './pret.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-pret',
    templateUrl: './pret.component.html'
})
export class PretComponent implements OnInit, OnDestroy {
prets: Pret[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pretService: PretService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pretService.query().subscribe(
            (res: HttpResponse<Pret[]>) => {
                this.prets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pret) {
        return item.id;
    }
    registerChangeInPrets() {
        this.eventSubscriber = this.eventManager.subscribe('pretListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
