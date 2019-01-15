import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CandidatService } from './candidat.service';
import { CandidatAggregate } from './candidat-wz.model';

@Component({
    selector: 'jhi-candidat-aggregate-detail',
    templateUrl: './candidat-aggregate-detail.component.html'
})
export class CandidatAggregateDetailComponent implements OnInit, OnDestroy {

    aggregate: CandidatAggregate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidatService: CandidatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidats();
    }

    load(id) {
        this.candidatService.findAggregate(id)
            .subscribe((candidatResponse: HttpResponse<CandidatAggregate>) => {
                this.aggregate = candidatResponse.body;
            });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandidats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidatListModification',
            (response) => this.load(this.aggregate.candidat.id)
        );
    }
}
