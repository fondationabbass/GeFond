import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Candidat } from './candidat.model';
import { CandidatService } from './candidat.service';

@Component({
    selector: 'jhi-candidat-detail',
    templateUrl: './candidat-detail.component.html'
})
export class CandidatDetailComponent implements OnInit, OnDestroy {

    candidat: Candidat;
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
        this.candidatService.find(id)
            .subscribe((candidatResponse: HttpResponse<Candidat>) => {
                this.candidat = candidatResponse.body;
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
            (response) => this.load(this.candidat.id)
        );
    }
}
