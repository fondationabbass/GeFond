import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Echeance } from './echeance.model';
import { EcheanceService } from './echeance.service';

@Injectable()
export class EcheancePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private echeanceService: EcheanceService

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
                this.echeanceService.find(id)
                    .subscribe((echeanceResponse: HttpResponse<Echeance>) => {
                        const echeance: Echeance = echeanceResponse.body;
                        if (echeance.dateTombe) {
                            echeance.dateTombe = {
                                year: echeance.dateTombe.getFullYear(),
                                month: echeance.dateTombe.getMonth() + 1,
                                day: echeance.dateTombe.getDate()
                            };
                        }
                        if (echeance.datePayement) {
                            echeance.datePayement = {
                                year: echeance.datePayement.getFullYear(),
                                month: echeance.datePayement.getMonth() + 1,
                                day: echeance.datePayement.getDate()
                            };
                        }
                        if (echeance.dateRetrait) {
                            echeance.dateRetrait = {
                                year: echeance.dateRetrait.getFullYear(),
                                month: echeance.dateRetrait.getMonth() + 1,
                                day: echeance.dateRetrait.getDate()
                            };
                        }
                        this.ngbModalRef = this.echeanceModalRef(component, echeance);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.echeanceModalRef(component, new Echeance());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    echeanceModalRef(component: Component, echeance: Echeance): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.echeance = echeance;
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
