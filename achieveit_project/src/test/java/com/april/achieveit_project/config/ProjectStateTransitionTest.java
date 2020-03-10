package com.april.achieveit_project.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import static com.april.achieveit_project.config.ProjectStateTransition.ProjectState.*;
import static org.junit.jupiter.api.Assertions.*;

class ProjectStateTransitionTest
{

    @Test
    void isValidTransition()
    {
        ProjectStateTransition transition=ProjectStateTransition.getInstance();
        assertTrue(transition.isValidTransition(Applied,Initiated));
        assertTrue(transition.isValidTransition(Applied,Rejected));
        assertTrue(transition.isValidTransition(Initiated,Developing));
        assertTrue(transition.isValidTransition(Developing,Delivered));
        assertTrue(transition.isValidTransition(Rejected,Applied));
        assertTrue(transition.isValidTransition(Delivered,Finished));
        assertTrue(transition.isValidTransition(Finished,ReadyArchive));
        assertTrue(transition.isValidTransition(ReadyArchive,Archived));
        assertTrue(transition.isValidTransition(ReadyArchive,ArchiveDeclined));
        assertTrue(transition.isValidTransition(ArchiveDeclined,ReadyArchive));

        assertFalse(transition.isValidTransition(Applied,Developing));
        assertFalse(transition.isValidTransition(Rejected,Developing));
        assertFalse(transition.isValidTransition(Developing,Finished));
        assertFalse(transition.isValidTransition(Developing,Archived));
        assertFalse(transition.isValidTransition(ArchiveDeclined,Archived));
    }
}