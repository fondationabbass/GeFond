import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SessionProjet } from './session-projet.model';
import { SessionProjetService } from './session-projet.service';

@Injectable()
export class SessionProjetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private sessionProjetService: SessionProjetService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.sessionProjetService.find(id)
                    .subscribe((sessionProjetResponse: HttpResponse<SessionProjet>) => {
                        const sessionProjet: SessionProjet = sessionProjetResponse.body;
                        if (sessionProjet.dateOuvert) {
                            sessionProjet.dateOuvert = {
                                year: sessionProjet.dateOuvert.getFullYear(),
                                month: sessionProjet.dateOuvert.getMonth() + 1,
                                day: sessionProjet.dateOuvert.getDate()
                            };
                        }
                        if (sessionProjet.dateFermeture) {
                            sessionProjet.dateFermeture = {
                                year: sessionProjet.dateFermeture.getFullYear(),
                                month: sessionProjet.dateFermeture.getMonth() + 1,
                                day: sessionProjet.dateFermeture.getDate()
                            };
                        }
                        this.ngbModalRef = this.sessionProjetModalRef(component, sessionProjet);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sessionProjetModalRef(component, new SessionProjet());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sessionProjetModalRef(component: Component, sessionProjet: SessionProjet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sessionProjet = sessionProjet;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
