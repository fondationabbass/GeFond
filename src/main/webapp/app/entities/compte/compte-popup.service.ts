import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Compte } from './compte.model';
import { CompteService } from './compte.service';

@Injectable()
export class ComptePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private compteService: CompteService

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
                this.compteService.find(id)
                    .subscribe((compteResponse: HttpResponse<Compte>) => {
                        const compte: Compte = compteResponse.body;
                        if (compte.dateOuverture) {
                            compte.dateOuverture = {
                                year: compte.dateOuverture.getFullYear(),
                                month: compte.dateOuverture.getMonth() + 1,
                                day: compte.dateOuverture.getDate()
                            };
                        }
                        if (compte.dateDernierDebit) {
                            compte.dateDernierDebit = {
                                year: compte.dateDernierDebit.getFullYear(),
                                month: compte.dateDernierDebit.getMonth() + 1,
                                day: compte.dateDernierDebit.getDate()
                            };
                        }
                        this.ngbModalRef = this.compteModalRef(component, compte);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.compteModalRef(component, new Compte());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    compteModalRef(component: Component, compte: Compte): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.compte = compte;
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
