import { Component, Input, Output, EventEmitter, ViewChild, OnInit } from '@angular/core';

import { NgForm } from '@angular/forms';
import { Projet } from './projet.model';

@Component({
    selector: 'jhi-projet-edit',
    templateUrl: './projet-edit.component.html'
})
export class ProjetEditComponent implements OnInit {

    @Input() projet: Projet;
    @Output() valid = new EventEmitter<boolean>();
    @ViewChild('editForm') ngForm: NgForm;

    ngOnInit() {
        this.ngForm.form.valueChanges.subscribe(x => {
          this.sendValid(this.ngForm.valid);
        })
    }

    sendValid(val: boolean) {
        this.valid.emit(val);
    }
}
