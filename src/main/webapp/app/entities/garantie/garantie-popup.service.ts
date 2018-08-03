import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Garantie } from './garantie.model';
import { GarantieService } from './garantie.service';
import { PretService, Pret } from '../pret';
import { dateToNgb } from '../../shared/model/format-utils';

@Injectable()
export class GarantiePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private garantieService: GarantieService,
        private pretService:PretService

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
                this.pretService.find(id)
                    .subscribe((resp: HttpResponse<Pret>) => {
                        const garantie: Garantie = new Garantie();
                        garantie.pret = resp.body;
                        garantie.dateDepot = dateToNgb(new Date());
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
