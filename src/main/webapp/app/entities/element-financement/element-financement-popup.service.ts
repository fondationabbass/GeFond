import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ElementFinancement } from './element-financement.model';
import {PretService, Pret} from '../pret';

@Injectable()
export class ElementFinancementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pretService: PretService

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
                        const elementFinancement: ElementFinancement = new ElementFinancement();
                        elementFinancement.pret = resp.body;
                        this.ngbModalRef = this.elementFinancementModalRef(component, elementFinancement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.elementFinancementModalRef(component, new ElementFinancement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    elementFinancementModalRef(component: Component, elementFinancement: ElementFinancement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.elementFinancement = elementFinancement;
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
