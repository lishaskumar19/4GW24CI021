#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Read a string safely */
void readString(char str[], size_t maxlen, const char *label)
{
    int c;
    printf("Enter %s: ", label);

    if (fgets(str, (int)maxlen, stdin) == NULL)
    {
        str[0] = '\0';
        return;
    }

    size_t len = strlen(str);
    if (len > 0 && str[len - 1] == '\n')
    {
        str[len - 1] = '\0';
    }
    else
    {
        while ((c = getchar()) != '\n' && c != EOF);
    }
}

/* Find string length without strlen */
int stringLength(char str[])
{
    int len = 0;
    while (str[len] != '\0')
        len++;
    return len;
}

/* Check whether pattern matches at position POS */
int isMatch(char str[], char pat[], int POS)
{
    int i = 0;
    while (pat[i] != '\0')
    {
        if (str[POS + i] != pat[i])
            return 0;
        i++;
    }
    return 1;
}

/* Replace pattern with replacement string */
void replacePattern(char str[], char pat[], char rep[], char result[])
{
    int i = 0, j = 0;
    int found = 0;

    int lenSTR = stringLength(str);
    int lenPAT = stringLength(pat);
    int lenREP = stringLength(rep);

    while (i < lenSTR)
    {
        if (isMatch(str, pat, i))
        {
            found = 1;
            for (int k = 0; k < lenREP; k++)
            {
                result[j++] = rep[k];
            }
            i += lenPAT;
        }
        else
        {
            result[j++] = str[i++];
        }
    }

    result[j] = '\0';

    if (found)
        printf("\nUpdated String: %s\n", result);
    else
        printf("\nPattern not found in the string.\n");
}

int main()
{
    char STR[100], PAT[50], REP[50], RESULT[200];

    readString(STR, sizeof(STR), "Main string (STR)");
    readString(PAT, sizeof(PAT), "Pattern string (PAT)");
    readString(REP, sizeof(REP), "Replace string (REP)");

    replacePattern(STR, PAT, REP, RESULT);

    return 0;
}
