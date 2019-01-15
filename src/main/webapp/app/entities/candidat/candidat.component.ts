import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Candidat } from './candidat.model';
import { CandidatService } from './candidat.service';
import { Principal } from '../../shared';
import { Router } from '@angular/router';
import { CandidatureWzService } from '../../shared/candidature-wz.service';
import { CandidatAggregate } from './candidat-wz.model';

@Component({
    selector: 'jhi-candidat',
    templateUrl: './candidat.component.html'
})
export class CandidatComponent implements OnInit, OnDestroy {
    candidats: Candidat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private router: Router,
        private candidatureWz: CandidatureWzService,
        private candidatService: CandidatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.candidatService.query().subscribe(
            (res: HttpResponse<Candidat[]>) => {
                this.candidats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCandidats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Candidat) {
        return item.id;
    }

    registerChangeInCandidats() {
        this.eventSubscriber = this.eventManager.subscribe('candidatListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

/*     startCandidature(candidat: Candidat) {
        this.candidatureWz.reset();
        this.candidatureWz.aggregate.candidature.candidat = candidat;
        this.router.navigate(['/candidature-wz']);
    }
 */
    startCandidature(candidat: Candidat) {
        this.candidatService.findAggregate(candidat.nni).subscribe(
            (res: HttpResponse<CandidatAggregate>) => {
                this.candidatureWz.reset();
                this.candidatureWz.aggregate.candidature.candidat = candidat;
                this.candidatureWz.aggregate.exps = res.body.exps;
                this.router.navigate(['/candidature-wz']);
            },
            () => {
                this.candidatureWz.reset();
                this.candidatureWz.aggregate.candidature.candidat = candidat;
                this.router.navigate(['/candidature-wz']);
            }
        );
    }
}
