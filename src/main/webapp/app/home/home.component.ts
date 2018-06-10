import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Candidat } from '../entities/candidat/candidat.model';
import { CandidatService } from '../entities/candidat/candidat.service';

import { ClientService } from '../entities/client/client.service';
import { Client } from '../entities/client/client.model';

import { PretService } from '../entities/pret/pret.service';
import { Pret } from '../entities/pret/pret.model';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    candidats:Candidat[];
    clients:Client[];
    prets:Pret[];
    modalRef: NgbModalRef;

    constructor(
        private candidatService: CandidatService,
        private clientService: ClientService,
        private pretService: PretService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.lastCandidats();
        this.lastClients();
        this.lastPrets();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }
    lastCandidats() {
        this.candidatService.last().subscribe(
            (res: HttpResponse<Candidat[]>) => {
                this.candidats = res.body;
            },
        );
    }
    lastClients() {
        this.clientService.last().subscribe(
            (res: HttpResponse<Client[]>) => {
                this.clients= res.body;
            },
        );
    }

    lastPrets() {
        this.pretService.last().subscribe(
            (res: HttpResponse<Pret[]>) => {
                this.prets = res.body;
            },
        );
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.lastCandidats();
                this.lastClients();
                this.lastPrets();
                this.account = account;
            });
        });
    }

    trackCandidatId(candidat: Candidat) {return candidat.id;}
    trackClientId(client: Candidat) {return client.id;}
    trackPretId(pret: Pret) {return pret.id;}
    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
