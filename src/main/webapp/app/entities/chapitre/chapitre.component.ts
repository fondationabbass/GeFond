import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Chapitre } from './chapitre.model';
import { ChapitreService } from './chapitre.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-chapitre',
    templateUrl: './chapitre.component.html'
})
export class ChapitreComponent implements OnInit, OnDestroy {
chapitres: Chapitre[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chapitreService: ChapitreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.chapitreService.query().subscribe(
            (res: HttpResponse<Chapitre[]>) => {
                this.chapitres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChapitres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Chapitre) {
        return item.id;
    }
    registerChangeInChapitres() {
        this.eventSubscriber = this.eventManager.subscribe('chapitreListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
