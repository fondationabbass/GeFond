import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Mouvement } from './mouvement.model';
import { MouvementService } from './mouvement.service';
import { PretService, Pret } from '../pret';
import { dateToNgb } from '../../shared/model/format-utils';

@Injectable()
export class MouvementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private pretService: PretService,
        private mouvementService: MouvementService

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
                        const mouvement: Mouvement = new Mouvement();
                        mouvement.pret = resp.body;
                        mouvement.lib='Rembourement';
                        mouvement.sens='+';
                        mouvement.dateMvt = dateToNgb(new Date());
                        this.ngbModalRef = this.mouvementModalRef(component, mouvement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mouvementModalRef(component, new Mouvement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mouvementModalRef(component: Component, mouvement: Mouvement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.mouvement = mouvement;
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
