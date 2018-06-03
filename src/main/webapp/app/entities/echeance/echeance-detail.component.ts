import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Echeance } from './echeance.model';
import { EcheanceService } from './echeance.service';

@Component({
    selector: 'jhi-echeance-detail',
    templateUrl: './echeance-detail.component.html'
})
export class EcheanceDetailComponent implements OnInit, OnDestroy {

    echeance: Echeance;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private echeanceService: EcheanceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEcheances();
    }

    load(id) {
        this.echeanceService.find(id)
            .subscribe((echeanceResponse: HttpResponse<Echeance>) => {
                this.echeance = echeanceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEcheances() {
        this.eventSubscriber = this.eventManager.subscribe(
            'echeanceListModification',
            (response) => this.load(this.echeance.id)
        );
    }
}
