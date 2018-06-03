import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Candidature } from './candidature.model';
import { CandidatureService } from './candidature.service';

@Component({
    selector: 'jhi-candidature-detail',
    templateUrl: './candidature-detail.component.html'
})
export class CandidatureDetailComponent implements OnInit, OnDestroy {

    candidature: Candidature;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private candidatureService: CandidatureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCandidatures();
    }

    load(id) {
        this.candidatureService.find(id)
            .subscribe((candidatureResponse: HttpResponse<Candidature>) => {
                this.candidature = candidatureResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCandidatures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'candidatureListModification',
            (response) => this.load(this.candidature.id)
        );
    }
}
