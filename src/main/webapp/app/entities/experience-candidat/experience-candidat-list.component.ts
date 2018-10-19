import { Component, Input } from '@angular/core';

import { ExperienceCandidat } from './experience-candidat.model';

@Component({
    selector: 'jhi-experience-candidat-list',
    templateUrl: './experience-candidat-list.component.html'
})
export class ExperienceCandidatListComponent {
    @Input() experienceCandidats: ExperienceCandidat[];
    @Input() fromDb: boolean = false;
    trackId(index: number, item: ExperienceCandidat) {
        return item.id;
    }
}
