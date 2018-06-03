import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExperienceCandidat } from './experience-candidat.model';
import { ExperienceCandidatService } from './experience-candidat.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-experience-candidat',
    templateUrl: './experience-candidat.component.html'
})
export class ExperienceCandidatComponent implements OnInit, OnDestroy {
experienceCandidats: ExperienceCandidat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private experienceCandidatService: ExperienceCandidatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.experienceCandidatService.query().subscribe(
            (res: HttpResponse<ExperienceCandidat[]>) => {
                this.experienceCandidats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInExperienceCandidats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ExperienceCandidat) {
        return item.id;
    }
    registerChangeInExperienceCandidats() {
        this.eventSubscriber = this.eventManager.subscribe('experienceCandidatListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
