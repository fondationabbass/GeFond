import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Garantie } from './garantie.model';
import { GarantieService } from './garantie.service';

@Injectable()
export class GarantiePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private garantieService: GarantieService

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
                this.garantieService.find(id)
                    .subscribe((garantieResponse: HttpResponse<Garantie>) => {
                        const garantie: Garantie = garantieResponse.body;
                        if (garantie.dateDepot) {
                            garantie.dateDepot = {
                                year: garantie.dateDepot.getFullYear(),
                                month: garantie.dateDepot.getMonth() + 1,
                                day: garantie.dateDepot.getDate()
                            };
                        }
                        if (garantie.dateRetrait) {
                            garantie.dateRetrait = {
                                year: garantie.dateRetrait.getFullYear(),
                                month: garantie.dateRetrait.getMonth() + 1,
                                day: garantie.dateRetrait.getDate()
                            };
                        }
                        this.ngbModalRef = this.garantieModalRef(component, garantie);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.garantieModalRef(component, new Garantie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    garantieModalRef(component: Component, garantie: Garantie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.garantie = garantie;
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
