import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';
import { CandidatureAggregate } from './candidature-wz.model';
import { CandidatureService } from '.';


@Component({
    selector: 'jhi-candidature-aggregate-detail',
    templateUrl: './candidature-aggregate-detail.component.html'
})
export class CandidatureAggregateDetailComponent implements OnInit, OnDestroy {

    aggregate: CandidatureAggregate;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidatService: CandidatureService,
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
            .subscribe((candidatResponse: HttpResponse<CandidatureAggregate>) => {
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
            () => this.load(this.aggregate.candidature.id)
        );
    }
}
