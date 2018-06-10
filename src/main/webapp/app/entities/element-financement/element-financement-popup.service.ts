import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ElementFinancement } from './element-financement.model';
import { ElementFinancementService } from './element-financement.service';

@Injectable()
export class ElementFinancementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private elementFinancementService: ElementFinancementService

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
                this.elementFinancementService.find(id)
                    .subscribe((elementFinancementResponse: HttpResponse<ElementFinancement>) => {
                        const elementFinancement: ElementFinancement = elementFinancementResponse.body;
                        if (elementFinancement.dateFinancement) {
                            elementFinancement.dateFinancement = {
                                year: elementFinancement.dateFinancement.getFullYear(),
                                month: elementFinancement.dateFinancement.getMonth() + 1,
                                day: elementFinancement.dateFinancement.getDate()
                            };
                        }
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
