import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Pret } from './pret.model';
import { PretService } from './pret.service';

@Component({
    selector: 'jhi-pret-detail',
    templateUrl: './pret-detail.component.html'
})
export class PretDetailComponent implements OnInit, OnDestroy {

    pret: Pret;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pretService: PretService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrets();
    }

    load(id) {
        this.pretService.find(id)
            .subscribe((pretResponse: HttpResponse<Pret>) => {
                this.pret = pretResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pretListModification',
            (response) => this.load(this.pret.id)
        );
    }
}
