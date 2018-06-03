import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ExperienceCandidat } from './experience-candidat.model';
import { ExperienceCandidatService } from './experience-candidat.service';

@Component({
    selector: 'jhi-experience-candidat-detail',
    templateUrl: './experience-candidat-detail.component.html'
})
export class ExperienceCandidatDetailComponent implements OnInit, OnDestroy {

    experienceCandidat: ExperienceCandidat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private experienceCandidatService: ExperienceCandidatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExperienceCandidats();
    }

    load(id) {
        this.experienceCandidatService.find(id)
            .subscribe((experienceCandidatResponse: HttpResponse<ExperienceCandidat>) => {
                this.experienceCandidat = experienceCandidatResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExperienceCandidats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'experienceCandidatListModification',
            (response) => this.load(this.experienceCandidat.id)
        );
    }
}
