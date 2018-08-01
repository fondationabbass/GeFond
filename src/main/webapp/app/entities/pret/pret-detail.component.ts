import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Pret } from './pret.model';
import { PretService } from './pret.service';
import { Garantie, GarantieService } from '../garantie';
import { ElementFinancement, ElementFinancementService } from '../element-financement';
import { Echeance, EcheanceService } from '../echeance';
import { Mouvement, MouvementService } from '../mouvement';

@Component({
    selector: 'jhi-pret-detail',
    templateUrl: './pret-detail.component.html'
})
export class PretDetailComponent implements OnInit, OnDestroy {
    title: string = "Détail d'un pret : ";
    events: string[] = ['pretListModification','garantieListModification',
                        'echeanceListModification','elementFinancementListModification','mouvementListModification'];
    pret: Pret;
    garanties:Garantie[];
    elementFinancements: ElementFinancement[];
    echeances:Echeance[];
    mouvements:Mouvement[];

    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pretService: PretService,
        private echeanceService: EcheanceService,
        private efService: ElementFinancementService,
        private garantieService: GarantieService,
        private mouvmentService: MouvementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrets();
    }
    emptyEf(): boolean {
        return this.elementFinancements.length === 0;
    }
    emptyG(): boolean {
        return this.garanties.length === 0;
    }
    emptyE(): boolean {
        return this.echeances.length === 0;
    }
    emptyM(): boolean {
        return this.mouvements.length === 0;
    }
    load(id) {
        this.pretService.find(id)
            .subscribe((pretResponse: HttpResponse<Pret>) => {
                this.pret = pretResponse.body;
            });
        this.efService.query({ "pretId.equals": id }).subscribe((pretResponse: HttpResponse<ElementFinancement[]>) => {
            this.elementFinancements = pretResponse.body;
        });
        this.garantieService.query({ "pretId.equals": id }).subscribe((pretResponse: HttpResponse<Garantie[]>) => {
            this.garanties = pretResponse.body;
        });
        this.echeanceService.query({ "pretId.equals": id }).subscribe((pretResponse: HttpResponse<Echeance[]>) => {
            this.echeances = pretResponse.body;
        });
        this.mouvmentService.query({ "pretId.equals": id , "lib.equals":"Rembourement"}).subscribe((pretResponse: HttpResponse<Mouvement[]>) => {
            this.mouvements = pretResponse.body;
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
        this.events.forEach(element => {
            this.eventSubscriber = this.eventManager.subscribe(
                element,
                (response) => this.load(this.pret.id)
            );
        });
    }
}
